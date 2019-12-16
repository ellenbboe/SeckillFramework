package sf.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sf.entity.Ord;
import sf.entity.OrdExample;

public interface OrdMapper {
    int countByExample(OrdExample example);

    int deleteByExample(OrdExample example);

    int deleteByPrimaryKey(String id);

    int insert(Ord record);

    int insertSelective(Ord record);

    List<Ord> selectByExample(OrdExample example);

    Ord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Ord record, @Param("example") OrdExample example);

    int updateByExample(@Param("record") Ord record, @Param("example") OrdExample example);

    int updateByPrimaryKeySelective(Ord record);

    int updateByPrimaryKey(Ord record);
}