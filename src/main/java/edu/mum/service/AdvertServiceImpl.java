package edu.mum.service;

import edu.mum.domain.Advert;
import edu.mum.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    AdvertRepository advertRepository;

    @Override
    public Advert saveAdvert(Advert advert) {
        return advertRepository.save(advert);
    }

    @Override
    public List<Advert> getAdverts() {
        return (List<Advert>) advertRepository.findAll();
    }

    @Override
    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id).get();
    }
}
