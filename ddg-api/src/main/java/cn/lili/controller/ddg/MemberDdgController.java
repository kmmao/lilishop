package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * 嘟嘟罐端,会员接口
 *
 * @author Allen
 * @since 2022/10/19 10:07 下午
 */
@Slf4j
@RestController
@Api(tags = "嘟嘟罐端,会员接口")
@RequestMapping("/ddg/member")
public class MemberDdgController {

    @Autowired
    private MemberService memberService;


    @ApiOperation(value = "嘟嘟罐注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "ddgId", value = "嘟嘟罐ID", required = true, paramType = "query")
    })
    @PostMapping("/ddgLogin")
    public ResultMessage<Object> ddgLogin(@NotNull(message = "手机号为空") @RequestParam String mobile,
                                          @NotNull(message = "嘟嘟罐ID") @RequestParam String ddgId) {
        return ResultUtil.data(memberService.mobilePhoneLoginByDdgId(mobile, ddgId));
    }

    @ApiOperation(value = "嘟嘟罐用户删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddgId", value = "嘟嘟罐ID", required = true, paramType = "query")
    })
    @PostMapping("/ddgDelete")
    public ResultMessage<Object> ddgDelete(@NotNull(message = "嘟嘟罐ID") @RequestParam String ddgId) {
        return ResultUtil.data(memberService.memberDeleteByDdgId(ddgId));
    }

    @ApiOperation(value = "通过嘟嘟罐id获取用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddgId", value = "嘟嘟罐ID", required = true, paramType = "query")
    })
    @PostMapping("/getMemberIdByDdgId")
    public ResultMessage<Object> getMemberIdByDdgId(@NotNull(message = "嘟嘟罐ID") @RequestParam String ddgId) {
        return ResultUtil.data(memberService.getMemberIdByDdgId(ddgId));
    }

}
