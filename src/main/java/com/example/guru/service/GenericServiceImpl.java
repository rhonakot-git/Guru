package com.example.guru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guru.entity.MGeneric;
import com.example.guru.repository.MGenericRepository;

@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private MGenericRepository mGenericRepository;

    @Override
    public List<MGeneric> getMasterDataByCategory(String category) {
        return mGenericRepository.findByCategoryOrderBySortOrder(category);
    }
}