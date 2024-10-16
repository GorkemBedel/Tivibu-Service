package com.Test.Tivibu.rule;

import com.Test.Tivibu.exception.WrongParameterException;
import org.springframework.stereotype.Component;

@Component
public class TesterValidator {

    public void validateTesterId(Long testerId) {

        int length = String.valueOf(testerId).length();

        if(length != 6) {
            throw new WrongParameterException("Sicil numarası (tester_id)  parametresi 6 haneli olmalıdır.");
        }
    }
}
