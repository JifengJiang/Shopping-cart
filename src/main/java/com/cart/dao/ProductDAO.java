package com.cart.dao;

import com.cart.entity.Product;
import com.cart.model.PaginationResult;
import com.cart.model.ProductInfo;

public interface ProductDAO {



    public Product findProduct(String code);

    public ProductInfo findProductInfo(String code) ;


    public PaginationResult<ProductInfo> queryProducts(int page,
                                                       int maxResult, int maxNavigationPage  );

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                                                       int maxNavigationPage, String likeName);

    public void save(ProductInfo productInfo);

}