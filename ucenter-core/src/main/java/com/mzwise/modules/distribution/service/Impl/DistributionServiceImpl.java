package com.mzwise.modules.distribution.service.Impl;

import com.mzwise.constant.QuantificationLevelEnum;
import com.mzwise.modules.distribution.service.DistributionService;
import com.mzwise.modules.distribution.vo.NumOfDiffTeamUnderLevel;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcInviteRecordService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.vo.SimpleMemberTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mzwise.constant.DistributionUpgradeRulesConstant.*;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@Service
public class DistributionServiceImpl implements DistributionService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcInviteRecordService inviteRecordService;

    @Override
    public SimpleMemberTreeVO queryTreeByMemberId(Long memberId) {
        UcMember supMember = memberService.getById(memberId);
        if (supMember == null) {
            return null;
        }
        // 返回用户下级
        List<UcMember> subMemberList = memberService.getByPid(memberId);
        List<SimpleMemberTreeVO> simpleMemberTreeVOList = new ArrayList<>();
        // 将Member对象转为simpleMember对象
        SimpleMemberTreeVO simpleMemberTreeVO = SimpleMemberTreeVO.memberTransToSimMember(supMember);
        //如果这个人没有下级
        if (subMemberList.isEmpty()) {
            simpleMemberTreeVOList.add(simpleMemberTreeVO);
        }
        // 对下级用户递归调用此方法
        for (UcMember ucMember : subMemberList) {
            SimpleMemberTreeVO subSimMember = queryTreeByMemberId(ucMember.getId());
            simpleMemberTreeVOList.add(subSimMember);
        }
        // 如果用户有下级 就setChildrenList
        if (!subMemberList.isEmpty()) {
            simpleMemberTreeVO.setChildrenList(simpleMemberTreeVOList);
        }
        return simpleMemberTreeVO;
    }

    @Override
    public Long getTopPid(Long memberId) {
        UcMember member = memberService.getById(memberId);
        // 如果当前用户已经是最顶级 则直接返回memberId
        if (member.getParentId() == null || member.getParentId().equals(0L)) {
            return memberId;
        }
        // 如果当前用户有上级
        Long pid = member.getParentId();
        while (true) {
            member = memberService.getById(pid);
            if (member == null || member.getParentId().equals(0L)) {
                return pid;
            }
            pid = member.getParentId();
        }
    }

    @Override
    public void updateIsEffective(Long memberId) {
        UcMember member = memberService.getById(memberId);
        Boolean isEffective = member.getIsEffective();
        if (isEffective) {
            return;
        }
        member.setIsEffective(true);
        member.setEffectiveTime(new Date());
        memberService.updateById(member);
        Long parentId = member.getParentId();
        if (parentId != null && parentId != 0L) {
            UcInviteRecord inviteRecord = inviteRecordService.queryBySubMemberId(memberId);
            if (inviteRecord != null) {
                inviteRecord.setIsEffective(true);
                inviteRecord.setEffectiveTime(new Date());
                inviteRecordService.updateById(inviteRecord);
            }
        }
    }

    @Override
    public void updateLevel(Long memberId) {
        LocalDateTime beginDate = LocalDateTime.now();
        System.out.println("先执行 updateLevel方法");
        UcMember member = memberService.getById(memberId);

        /* 如果用户没有上级（上级为平台）则 do nothing */
        if (member.getParentId() == null || member.getParentId().equals(0L)) {
            return;
        }

        /* 如果上级的直属下级不等于6个 不触发更新等级 */
        UcMember supMember = memberService.getById(member.getParentId());
        if (!supMember.getFirstLevelNum().equals(ZERO2ONE - 1)) {
            return;
        }
        Long topPid = getTopPid(memberId);
        SimpleMemberTreeVO simpleMemberTreeVO = queryTreeByMemberId(topPid);
        updateLevel(simpleMemberTreeVO);
        LocalDateTime endDate = LocalDateTime.now();
        System.out.println("updateLevel方法执行完毕，任务耗时： " + Duration.between(beginDate, endDate).getSeconds());
    }

    private void updateLevel(SimpleMemberTreeVO supMemberTreeVO) {
        List<SimpleMemberTreeVO> childrenList = supMemberTreeVO.getChildrenList();
        if (childrenList == null || childrenList.isEmpty()) {
            return;
        }
        for (SimpleMemberTreeVO memberTreeVO : childrenList) {
            updateLevel(memberTreeVO);
            updateNumOfDiffTeamUnderLevel(memberTreeVO, supMemberTreeVO);
        }
    }

    private void updateNumOfDiffTeamUnderLevel(SimpleMemberTreeVO subMemberTreeVO, SimpleMemberTreeVO supMemberTreeVO) {
        QuantificationLevelEnum subQuantificationLevel = subMemberTreeVO.getQuantificationLevel();

        NumOfDiffTeamUnderLevel subNumOfDiffTeam = subMemberTreeVO.getNumOfDiffTeamUnderLevel();
        Integer numOfLevel1 = subNumOfDiffTeam.getNumOfLevel1();
        Integer numOfLevel2 = subNumOfDiffTeam.getNumOfLevel2();
        Integer numOfLevel3 = subNumOfDiffTeam.getNumOfLevel3();
        Integer numOfLevel4 = subNumOfDiffTeam.getNumOfLevel4();
        Integer numOfLevel5 = subNumOfDiffTeam.getNumOfLevel5();
        Integer numOfLevel6 = subNumOfDiffTeam.getNumOfLevel6();
        switch (subQuantificationLevel) {
            case LEVEL1:
                subNumOfDiffTeam.setNumOfLevel1(numOfLevel1 + 1);
                break;
            case LEVEL2:
                subNumOfDiffTeam.setNumOfLevel2(numOfLevel2 + 1);
                break;
            case LEVEL3:
                subNumOfDiffTeam.setNumOfLevel3(numOfLevel3 + 1);
                break;
            case LEVEL4:
                subNumOfDiffTeam.setNumOfLevel4(numOfLevel4 + 1);
                break;
            case LEVEL5:
                subNumOfDiffTeam.setNumOfLevel5(numOfLevel5 + 1);
                break;
            case LEVEL6:
                subNumOfDiffTeam.setNumOfLevel6(numOfLevel6 + 1);
                break;
            default:
                break;
        }
        NumOfDiffTeamUnderLevel supNumOfDiffTeam = supMemberTreeVO.getNumOfDiffTeamUnderLevel();
        if (subNumOfDiffTeam.getNumOfLevel1() >= 1) {
            supNumOfDiffTeam.setNumOfLevel1(supNumOfDiffTeam.getNumOfLevel1() + 1);
        } else if (subNumOfDiffTeam.getNumOfLevel2() >= 1) {
            supNumOfDiffTeam.setNumOfLevel2(supNumOfDiffTeam.getNumOfLevel2() + 1);
        } else if (subNumOfDiffTeam.getNumOfLevel3() >= 1) {
            supNumOfDiffTeam.setNumOfLevel3(supNumOfDiffTeam.getNumOfLevel3() + 1);
        } else if (subNumOfDiffTeam.getNumOfLevel4() >= 1) {
            supNumOfDiffTeam.setNumOfLevel4(supNumOfDiffTeam.getNumOfLevel4() + 1);
        } else if (subNumOfDiffTeam.getNumOfLevel5() >= 1) {
            supNumOfDiffTeam.setNumOfLevel5(supNumOfDiffTeam.getNumOfLevel5() + 1);
        } else if (subNumOfDiffTeam.getNumOfLevel6() >= 1) {
            supNumOfDiffTeam.setNumOfLevel6(supNumOfDiffTeam.getNumOfLevel6() + 1);
        }
        UcMember ucMember = SimpleMemberTreeVO.simpleMemberTransToMember(supMemberTreeVO);
        assert ucMember != null;
        // 用户自身有效且满足条件
        if (supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL0)) {
            if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getChildrenList().size() >= ZERO2ONE) {
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL1);
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL1);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL1)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel1() >= ONE2TWO) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL2);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL2);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL2)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel2() >= TWO2THREE) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL3);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL3);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL3)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel3() >= THREE2FOUR) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL4);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL4);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL4)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel4() >= FOUR2FIVE) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL5);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL5);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL5)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel5() >= FIVE2SIX) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL6);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL6);
            }
        } else if (supMemberTreeVO.getChildrenList() != null && supMemberTreeVO.getIsEffective() && supMemberTreeVO.getQuantificationLevel().equals(QuantificationLevelEnum.LEVEL6)) {
            if (supMemberTreeVO.getNumOfDiffTeamUnderLevel().getNumOfLevel5() >= SIX2SEVEN) {
                ucMember.setQuantificationLevel(QuantificationLevelEnum.LEVEL7);
                supMemberTreeVO.setQuantificationLevel(QuantificationLevelEnum.LEVEL7);
            }
        }
        memberService.updateById(ucMember);
    }

}

