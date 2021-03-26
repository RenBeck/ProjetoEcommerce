package com.hatanaka.ecommerce.payment.listener;
import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.hatanaka.ecommerce.payment.streaming.CheckoutProcessor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {

    private  final  CheckoutProcessor checkoutProcessor;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event){

        final PaymentCreatedEvent checkoutCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(event.getCheckoutCode())
                .setChecoltStatus(event.getStatus())
                .setPaymentCode(UUID.randomUUID().toString())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload().build());
    }
}
