package com.jgm.minecraftapp.service;

import com.jgm.minecraftapp.model.Block;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BlockService {

    @GET("blocks")
    Call<List<Block>> getBlocks();
}
