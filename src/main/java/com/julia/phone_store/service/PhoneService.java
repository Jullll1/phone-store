package com.julia.phone_store.service;

import com.julia.phone_store.model.Phone;
import com.julia.phone_store.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public Optional<Phone> getPhoneById(Long id) {
        return phoneRepository.findById(id);
    }

    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }
}