package com.eva.EvaSupportAgent.product.service;

import java.util.List;

import com.eva.EvaSupportAgent.product.vo.OrderHistoryVO;

public interface OrderHistoryService {

    List<OrderHistoryVO> getAll();

    List<OrderHistoryVO> getByUserId(Long userId);

    OrderHistoryVO save(OrderHistoryVO vo);
}