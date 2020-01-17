package sf.service;

import sf.entity.Goods;
import sf.model.SeckillGoodsModel;

import java.util.List;

public interface GoodsService {
    List<Goods> GetGoodsList();
    Goods getGoodsById(int id);
    SeckillGoodsModel GoodsToModel(Goods goods);
    void updateModel(int goodsId);
    boolean haveStock(int goodsId);
    String createRandomPath(int goodsId);
    boolean checkRandomPath(String path,int goodsId);
}
