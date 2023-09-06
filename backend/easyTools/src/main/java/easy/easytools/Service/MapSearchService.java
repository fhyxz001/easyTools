package easy.easytools.Service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import easy.easytools.Entity.Address;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapSearchService {
    private final String userKey = "ff5b81ccf61d10134fc0b2cf173d5c73";
    private final String baseSearchUrl = "https://restapi.amap.com/v3/assistant/inputtips?key="+userKey;
    private final String baseAnalyzeUrl = "https://restapi.amap.com/v3/direction/driving?key="+userKey;
    int count;
    private LinkedList<Address> sortPath = new LinkedList<>();
    public JSONObject mapSearch(Map<String,String> param){
        String address = param.get("address");
        String city = param.get("city");
        StringBuilder searchUrl = new StringBuilder(baseSearchUrl);
        searchUrl.append("&keywords=").append(address);
        searchUrl.append("&datatype=").append("all");
        if(city != null){
            searchUrl.append("&city=").append(city);
        }
        String finalUrl = searchUrl.toString();
        String res = HttpUtil.post(finalUrl, "");
        try {
            JSONObject resJson = JSONUtil.parseObj(res);
            return resJson;
        } catch (Exception e) {
            throw new ServiceException("JSON解析失败");
        }
    }


    public JSONObject analyze(List<Address> list) {
        Address start = list.stream().filter(address -> Objects.equals(address.getIsStart(), true)).findFirst().orElse(null);
        if(start==null){
            throw new ServiceException("未找到起点");
        }
        list.remove(start);
        sortPath= new LinkedList<>();
        start.setDistance(BigDecimal.ZERO);
        sortPath.add(start);
        count = 0;
        do {
            start = sortPath.size()==1?start:sortPath.getLast();
            list = findquick(start,list);
        }while (list.size()>0);
        System.out.println(sortPath);
        StringBuilder bestPath = new StringBuilder();
        for (Address address : sortPath) {
            bestPath.append(address.getName()).append("->");
        }
        bestPath.delete(bestPath.length()-2,bestPath.length());
        BigDecimal totalDistance = sortPath.stream().map(Address::getDistance).reduce(BigDecimal.ZERO, BigDecimal::add);
        JSONObject res = new JSONObject();
        res.put("bestPath",bestPath);
        res.put("detail",sortPath);
        res.put("totalDistance",totalDistance.compareTo(new BigDecimal(1000))>0?totalDistance.divide(new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP)+"公里":totalDistance+"米");
        res.put("apiCount",count);
        return res;
    }
    List<Address> findquick(Address start, List<Address> waitList){
        List<Map<Address,BigDecimal>> distances = new ArrayList<>();
        for (Address address : waitList) {
            //调用高德地图api计算距离 todo
            StringBuilder analyzeUrl = new StringBuilder(baseAnalyzeUrl);
            String origin = start.getLocation();
            analyzeUrl.append("&origin=").append(origin);
            String destination = address.getLocation();
            analyzeUrl.append("&destination=").append(destination);
            String finalUrl = analyzeUrl.toString();
            String res = HttpUtil.post(finalUrl, "");
            try {
                JSONObject resJson = JSONUtil.parseObj(res);
                Object route = resJson.get("route");
                JSONObject routeJson = JSONUtil.parseObj(route);
                Object paths = routeJson.get("paths");
                List<JSONObject> pathsJson = JSONUtil.parseArray(paths).toList(JSONObject.class);
                BigDecimal distance = BigDecimal.ZERO;
                if(pathsJson.size()>0){
                    for (JSONObject pathitem : pathsJson) {
                        Object distanceItem = pathitem.get("distance");
                        BigDecimal distanceBigD = new BigDecimal(distanceItem.toString());
                        distance = distance.add(distanceBigD);
                    }
                }
                Map<Address,BigDecimal> distanceMap = new HashMap<>();
                distanceMap.put(address,distance);
                distances.add(distanceMap);
                count++;

            } catch (Exception e) {
                throw new ServiceException("JSON解析失败");
            }

        }
        BigDecimal minDistance = BigDecimal.ZERO;
        Address minAddress = null;
        distances.sort(Comparator.comparing(o -> o.values().iterator().next()));
        minAddress = distances.get(0).keySet().iterator().next();
        minAddress.setDistance(distances.get(0).values().iterator().next());
        sortPath.add(minAddress);
        waitList.remove(minAddress);
        return waitList;
    }


}
