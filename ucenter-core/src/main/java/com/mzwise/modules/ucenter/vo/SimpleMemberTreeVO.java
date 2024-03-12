package com.mzwise.modules.ucenter.vo;

import com.mzwise.common.util.SpringUtil;
import com.mzwise.constant.QuantificationLevelEnum;
import com.mzwise.modules.distribution.vo.NumOfDiffTeamUnderLevel;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@Data
@ApiModel(value = "会员简化树形结构")
public class SimpleMemberTreeVO {
    private static final UcMemberService memberService;

    static {
        memberService = SpringUtil.getBean(UcMemberService.class);
    }

    @ApiModelProperty("会员id")
    private Long id;

    @ApiModelProperty("上级id")
    private Long pid;

    @ApiModelProperty("是否有效")
    private Boolean isEffective;

    @ApiModelProperty("某等级下 某个团队的数量")
    private NumOfDiffTeamUnderLevel numOfDiffTeamUnderLevel = new NumOfDiffTeamUnderLevel(0, 0, 0, 0, 0, 0);

    @ApiModelProperty(value = "量化等级")
    private QuantificationLevelEnum quantificationLevel;

    @ApiModelProperty("下级团队")
    private List<SimpleMemberTreeVO> childrenList;

    public static SimpleMemberTreeVO memberTransToSimMember(UcMember member) {
        SimpleMemberTreeVO simpleMemberTreeVO = new SimpleMemberTreeVO();
        simpleMemberTreeVO.setId(member.getId());
        simpleMemberTreeVO.setPid(member.getParentId());
        simpleMemberTreeVO.setIsEffective(member.getIsEffective());
        simpleMemberTreeVO.setQuantificationLevel(member.getQuantificationLevel());
        return simpleMemberTreeVO;
    }

    public static UcMember simpleMemberTransToMember(SimpleMemberTreeVO simpleMemberTreeVO) {
        Long id = simpleMemberTreeVO.getId();
        if (id == null) {
            return null;
        }
        return memberService.getById(id);
    }
}
