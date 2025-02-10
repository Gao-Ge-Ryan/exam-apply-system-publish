package top.gaogle.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.gaogle.common.RegisterConst;
import top.gaogle.dao.master.UserMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.util.*;
import top.gaogle.pojo.dto.RegistryDTO;
import top.gaogle.pojo.dto.VerificationCodeDTO;
import top.gaogle.pojo.entity.UserEntity;
import top.gaogle.pojo.enums.HttpStatusEnum;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService extends SuperService {

    @Value("${spring.mail.username}")
    public String platformMail;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final StringRedisTemplate stringRedisTemplate;


    @Autowired
    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, EmailService emailService, TemplateEngine templateEngine, StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public I18nResult<Object> registry(RegistryDTO registryDTO) {
        I18nResult<Object> result = I18nResult.newInstance();
        try {
            String username = registryDTO.getUsername();
            String password = registryDTO.getPassword();
            String nickname = registryDTO.getNickname();
            String verificationCodeParam = registryDTO.getVerificationCode();
            if (StringUtils.isAnyEmpty(username, password, nickname, verificationCodeParam)) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, "请输入正确的请求参数");
            }
            if (Boolean.FALSE.equals(ValidatorUtil.regex(RegisterConst.EMAIL_REGEX, username))) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, "请输入正确格式的邮箱账号");
            }
            if (Boolean.FALSE.equals(ValidatorUtil.regex(RegisterConst.PASSWORD_REGEX, password))) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, "密码必须包含大写字母、小写字母、数字和特殊字符(@#$%^&*!)，并且长度必须在8到28个字符之间");
            }

            String verificationCode = stringRedisTemplate.opsForValue().get(StringUtil.redisKey("user", "verificationCode", username));
            if (StringUtils.isEmpty(verificationCode)) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, "验证码失效，请重新获取验证码！");
            }
            if (!Objects.equals(verificationCodeParam, verificationCode)) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, "验证码错误！");
            }


            int userSize = userMapper.selectExistByUsername(username);
            if (userSize > 0) {
                return result.failed().setMessage(I18ResultCode.MESSAGE, username + "用户已注册");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(UniqueUtil.getUniqueId());
            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setNickname(nickname);
            userEntity.setUsername(username);
            userEntity.setCreateBy(username);
            userEntity.setUpdateBy(username);
            Long timeMillis = DateUtil.currentTimeMillis();
            userEntity.setCreateAt(timeMillis);
            userEntity.setUpdateAt(timeMillis);
            userEntity.setDelFlag(false);
            userEntity.setDisabled(false);
            userMapper.insertOne(userEntity);
            stringRedisTemplate.delete(StringUtil.redisKey("user", "verificationCode", username));
        } catch (Exception e) {
            log.error("注册失败：", e);
            return result.failed().setMessage(I18ResultCode.MESSAGE, "注册账号失败");
        }
        return result;
    }

    public I18nResult<Boolean> verificationCode(VerificationCodeDTO verificationCodeDTO) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String email = verificationCodeDTO.getEmail();
            if (StringUtils.isAnyEmpty(email)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            if (Boolean.FALSE.equals(ValidatorUtil.regex(RegisterConst.EMAIL_REGEX, email))) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "请输入正确格式的邮箱账号");
            }
            String captcha = CaptchaGeneratorUtil.generateCaptcha();
            stringRedisTemplate.opsForValue().set(StringUtil.redisKey("user", "verificationCode", email), captcha, 5, TimeUnit.MINUTES);
            Context context = new Context();
            context.setVariable("verificationCode", captcha);
            String content = templateEngine.process("verificationCodeTemplate.html", context);
            emailService.send("Style-Register", platformMail, email, "注册验证码", content, true, null, null, null);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("获取验证码发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "获取验证码发生异常");
        }
        return result;
    }
}
