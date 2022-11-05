package cn.lili.modules.system.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.system.entity.dos.Region;
import cn.lili.modules.system.entity.vo.RegionVO;
import cn.lili.modules.system.service.RegionService;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegionServiceImplTest {

    @Autowired
    private RegionService regionService;

    @Test
    void getAllCity() {
        List<RegionVO> allCity = regionService.getAllCity();
        List<NewRegion> allRegion = new ArrayList<>();
        for (int i = 0; i < allCity.size(); i++) {
            RegionVO regionVO = allCity.get(i);
            NewRegion region = new NewRegion();
            region.setValue(regionVO.getId());
            region.setText(regionVO.getName());
            region.setChildren(getChildren(regionVO.getId(),true));
            allRegion.add(region);
        }
        System.out.println(JSONUtil.toJsonStr(allRegion));
    }


    private List<NewRegion> getChildren(String id,boolean is_gono){
        List<NewRegion> newRegions = new ArrayList<>();
        List<Region> regions = regionService.getItem(id);
        for (int k = 0; k < regions.size(); k++) {
            Region region = regions.get(k);
            NewRegion newRegion = new NewRegion();
            newRegion.setValue(region.getId());
            newRegion.setText(region.getName());
            if(is_gono)
                newRegion.setChildren(getChildren(region.getId(),false));
            newRegions.add(newRegion);
        }
        return newRegions;
    }

    @Data
    class NewRegion {
        private String value;
        private String text;
        private List<NewRegion> children;

    }


}