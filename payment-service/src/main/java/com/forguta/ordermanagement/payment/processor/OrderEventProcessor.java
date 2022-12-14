package com.forguta.ordermanagement.payment.processor;

import com.forguta.libs.saga.core.process.annotation.Processor;
import com.forguta.ordermanagement.common.event.order.OrderCanceledEvent;
import com.forguta.ordermanagement.common.event.order.OrderCreatedEvent;
import com.forguta.ordermanagement.payment.service.TransactionCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderEventProcessor {

    private final TransactionCommandService transactionCommandService;

    @Processor(OrderCreatedEvent.class)
    public void allocateInventoryConsumption(OrderCreatedEvent orderCreatedEvent) {
        transactionCommandService.doTransaction(orderCreatedEvent);
    }

    @Processor(OrderCanceledEvent.class)
    public void deallocateInventoryConsumption(OrderCanceledEvent orderCanceledEvent) {
        transactionCommandService.abortTransaction(orderCanceledEvent);
    }
}
