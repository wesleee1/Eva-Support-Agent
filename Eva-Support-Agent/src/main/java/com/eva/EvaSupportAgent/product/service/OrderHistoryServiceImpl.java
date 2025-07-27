package com.eva.EvaSupportAgent.product.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eva.EvaSupportAgent.product.mapper.OrderHistoryMapper;
import com.eva.EvaSupportAgent.product.model.OrderHistory;
import com.eva.EvaSupportAgent.product.repository.OrderHistoryRepository;
import com.eva.EvaSupportAgent.product.vo.OrderHistoryVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository repository;



    @Override
    public List<OrderHistoryVO> getAll() {
        return repository.findAll().stream()
                .map(OrderHistoryMapper::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderHistoryVO> getByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(OrderHistoryMapper::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderHistoryVO save(OrderHistoryVO vo) {
        OrderHistory entity = OrderHistoryMapper.toEntity(vo);
        entity.setOrderedOn(new Date());
        OrderHistory saved = repository.save(entity);
        return OrderHistoryMapper.toVO(saved);
    }
}
