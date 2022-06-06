package com.example.proyekpamkelompok06.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "keranjang")

public class Produk implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;

    public int id;
    public String nama;
    public String deskripsi;
    public int harga;
    public int stok;
    public int jumlah = 1;
    public String gambar;
    public String noKTPAdmin;
    public boolean select = true;

}
