package com.example.guru.service;

import java.util.List;

import com.example.guru.entity.MGeneric;

public interface GenericService {
    List<MGeneric> getMasterDataByCategory(String category);
}