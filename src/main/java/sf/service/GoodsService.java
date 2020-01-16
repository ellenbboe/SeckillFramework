package sf.service;

import sf.entity.Goods;
import sf.model.SeckillGoodsModel;

import java.util.List;

public interface GoodsService {
    List<Goods> GetGoodsList();
    Goods getGoodsById(int id);
    SeckillGoodsModel GoodsToModel(Goods goods);
    void updateModel(int goodsId);
}
