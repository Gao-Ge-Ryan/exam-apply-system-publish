package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.master.SpotInfoMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.pojo.enums.SpotInfoStatusEnum;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.pojo.model.SpotInfoModel;
import top.gaogle.pojo.param.SpotInfoEditParam;
import top.gaogle.pojo.param.SpotInfoQueryParam;

import java.util.List;
import java.util.Objects;

@Service
public class SpotInfoService extends SuperService {

    private final SpotInfoMapper spotInfoMapper;
    private final RegisterPublishMapper registerPublishMapper;

    @Autowired
    public SpotInfoService(SpotInfoMapper spotInfoMapper, RegisterPublishMapper registerPublishMapper) {
        this.spotInfoMapper = spotInfoMapper;
        this.registerPublishMapper = registerPublishMapper;
    }

    public I18nResult<Boolean> insert(SpotInfoEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String spot = editParam.getSpot();
            String spotAddress = editParam.getSpotAddress();
            Integer roomQuantity = editParam.getRoomQuantity();
            Integer seatQuantity = editParam.getSeatQuantity();
            if (StringUtils.isAnyEmpty(spot, spotAddress) || roomQuantity == null || seatQuantity == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            editParam.setStatus(SpotInfoStatusEnum.ENABLE);
            editParam.setEnterpriseId(SecurityUtil.getEnterpriseId());
            editParam.setId(UniqueUtil.getUniqueId());
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setCreateBy(loginUsername);
            editParam.setUpdateBy(loginUsername);
            spotInfoMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加发生异常");
        }
        return result;
    }


    public I18nResult<Boolean> putSpotInfo(SpotInfoEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            Long timeMillis = DateUtil.currentTimeMillis();
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setUpdateBy(loginUsername);
            editParam.setUpdateAt(timeMillis);
            editParam.setEnterpriseId(SecurityUtil.getEnterpriseId());
            spotInfoMapper.putSpotInfo(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("修改发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "修改发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<SpotInfoModel>> queryByPageAndCondition(SpotInfoQueryParam queryParam) {
        I18nResult<PageModel<SpotInfoModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            queryParam.setEnterpriseId(SecurityUtil.getEnterpriseId());
            List<SpotInfoModel> enterpriseModels = spotInfoMapper.queryByPageAndCondition(queryParam);
            PageModel<SpotInfoModel> pageModel = new PageModel<>(enterpriseModels);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询发生异常");
        }
        return result;
    }

    public I18nResult<SpotInfoModel> queryOneById(String id) {
        I18nResult<SpotInfoModel> result = I18nResult.newInstance();
        try {
            SpotInfoModel enterpriseModel = spotInfoMapper.queryOneById(id);
            result.succeed().setData(enterpriseModel);
        } catch (Exception e) {
            log.error("删除发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "删除企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> deleteById(String id) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            spotInfoMapper.deleteById(id);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("删除发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "删除发生异常");
        }
        return result;
    }

    public I18nResult<List<SpotInfoModel>> enterpriseEnableAll() {
        I18nResult<List<SpotInfoModel>> result = I18nResult.newInstance();
        try {
            String enterpriseId = SecurityUtil.getEnterpriseId();
            List<SpotInfoModel> spotInfoModels = spotInfoMapper.queryByEnableAndEnterpriseId(enterpriseId);
            result.succeed().setData(spotInfoModels);
        } catch (Exception e) {
            log.error("查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询发生异常");
        }
        return result;
    }

    public I18nResult<List<SpotInfoModel>> queryByRegisterPublishId(String registerPublishId) {
        I18nResult<List<SpotInfoModel>> result = I18nResult.newInstance();
        try {
            String enterpriseId = SecurityUtil.getEnterpriseId();
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("发布信息不存在");
            }
            String publishEnterpriseId = registerPublishModel.getEnterpriseId();
            if (!Objects.equals(publishEnterpriseId, enterpriseId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("没有权限操作");
            }
            List<SpotInfoModel> spotInfoModels = spotInfoMapper.queryByRegisterPublishId(registerPublishId);
            result.succeed().setData(spotInfoModels);
        } catch (Exception e) {
            log.error("查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询发生异常");
        }
        return result;
    }
}
