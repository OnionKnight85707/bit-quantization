package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.ucenter.entity.UcWallet;
import lombok.Data;

import java.util.List;

@Data
public class ExchangeOptionVO {

    private UcWallet from;

    private List<ExchangeTunnelVO> to;
}
