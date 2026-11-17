package org.example.demo.service;

import org.example.demo.repository.ArRepository;
import org.example.demo.repository.SutiRepository;
import org.example.demo.repository.TartalomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SutiService {

    private final SutiRepository sutiRepository;
    private final TartalomRepository tartalomRepository;
    private final ArRepository arRepository;

    public SutiService(SutiRepository sutiRepository,
                       TartalomRepository tartalomRepository,
                       ArRepository arRepository) {
        this.sutiRepository = sutiRepository;
        this.tartalomRepository = tartalomRepository;
        this.arRepository = arRepository;
    }


    @Transactional
    public void torol(Integer id) {
        tartalomRepository.deleteBySutiId(id);
        arRepository.deleteBySutiId(id);
        sutiRepository.deleteById(id);
    }
}
