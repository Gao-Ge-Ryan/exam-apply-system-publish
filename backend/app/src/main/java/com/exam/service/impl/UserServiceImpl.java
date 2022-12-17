package com.exam.service.impl;

import com.exam.common.Result;
import com.exam.common.Utils.DateUtil;
import com.exam.common.Utils.IDGenerator;
import com.exam.common.enums.impl.UserRoleEnum;

import com.exam.dao.UserDao;
import com.exam.pojo.model.UserModel;
import com.exam.pojo.param.UserParam;
import com.exam.security.util.BCryptUtil;
import com.exam.security.util.GetTokenInfoUtil;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author gaoge
 * @since 2021-11-11 11:13:35
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserModel queryById(String id) {
        return this.userDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param user 筛选条件
     * @param pageNum      当前页数
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @Override
    public PageInfo<UserModel> queryByPage(UserParam user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserModel> users =this.userDao.queryAllByLimit(user);
        return new PageInfo<>(users);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public Result insert(UserParam user) {
        UserModel userModel = userDao.selectByUserName(user.getUserName());
        if (userModel != null) {
            return Result.failed("该邮箱已被注册");
        }
        user.setId(IDGenerator.StringID());
        user.setCreateTime(DateUtil.getCurrentTimeMillis());
        user.setUpdateTime(DateUtil.getCurrentTimeMillis());
        user.setPassword(BCryptUtil.encode(user.getPassword()));
        this.userDao.insert(user);
        return Result.ok(user);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public UserModel update(UserParam user) {
        user.setUpdateTime(DateUtil.getCurrentTimeMillis());
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserModel selectByUserName(String username) {
        UserParam userParam = new UserParam();
        userParam.setUserName(username);
        List<UserModel> userModels = userDao.queryAllByLimit(userParam);
        if (userModels != null && userModels.size() > 0) {
            UserModel userModel = userModels.get(0);
            return userModel;
        }
        return null;
    }

    @Override
    public Result registeredUser(UserParam user) {
        String authCode = stringRedisTemplate.opsForValue().get("AuthCode"+user.getUserName());
        if(authCode==null|| !authCode.equals(user.getInputCode())){
            return Result.failed("验证码错误");
        }
        UserModel userModel = userDao.selectByUserName(user.getUserName());
        if (userModel != null) {
            return Result.failed("该邮箱已被注册");
        }
        user.setId(IDGenerator.StringID());
        user.setCreateTime(DateUtil.getCurrentTimeMillis());
        user.setUpdateTime(DateUtil.getCurrentTimeMillis());
        user.setPassword(BCryptUtil.encode(user.getPassword()));
        user.setRole(UserRoleEnum.User);
        user.setAvatar("http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif");
        user.setNickName("小考呀");
        this.userDao.insert(user);
        return Result.ok(user);
    }

    @Override
    public void patchInfo(UserParam user) {
        user.setUserName(GetTokenInfoUtil.getUsername());
        userDao.patchInfo(user);
    }

    @Override
    public Result updatePassword(UserParam user) {
        String username = GetTokenInfoUtil.getUsername();
        String authCode = stringRedisTemplate.opsForValue().get("AuthCode"+username);
        if(authCode==null|| !authCode.equals(user.getInputCode())){
            return Result.failed("验证码错误");
        }

        user.setPassword(BCryptUtil.encode(user.getPassword()));
        user.setUserName(username);
        userDao.patchInfo(user);
        return Result.ok(user);
    }

    @Override
    public UserModel getUserInfo() {
        String username = GetTokenInfoUtil.getUsername();
        UserModel userModel= userDao.selectByUserName(username);
        return userModel;
    }
}
