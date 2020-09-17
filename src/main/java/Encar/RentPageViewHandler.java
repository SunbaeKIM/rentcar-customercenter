package Encar;

import Encar.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentPageViewHandler {


    @Autowired
    private RentPageRepository rentPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentAccepted_then_CREATE (@Payload RentAccepted rentAccepted) {
        try {
            if (rentAccepted.isMe()) {
                // view 객체 생성
                RentPage rentPage = new RentPage();
                // view 객체에 이벤트의 Value 를 set 함
                rentPage.setRentId(rentAccepted.getRentId());
                rentPage.setAgencyName(rentAccepted.getAgencyname());
                rentPage.setCarId(rentAccepted.getCarId());
                rentPage.setStatus(rentAccepted.getStatus());
                // view 레파지 토리에 save
                rentPageRepository.save(rentPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentCanceled_then_UPDATE (@Payload RentCanceled rentCanceled) {
        try {
            if (rentCanceled.isMe()) {
                // view 객체 조회
                List<RentPage> rentList = rentPageRepository.findByRentId(rentCanceled.getRentId());
                for(RentPage rentPage : rentList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    rentPage.setStatus(rentCanceled.getStatus());
                    // view 레파지 토리에 save
                    rentPageRepository.save(rentPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}