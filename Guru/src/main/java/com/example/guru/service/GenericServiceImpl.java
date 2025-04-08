package com.example.guru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guru.entity.MGeneric;
import com.example.guru.repository.MGenericRepository;

/**
 * {@link GenericService} の実装クラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private MGenericRepository mGenericRepository;

    @Override
    public List<MGeneric> getCategoryMasterData(String category) {
        return mGenericRepository.findByCategoryOrderBySortOrder(category);
    }
}