package alone.walker.map.baidu;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: huangYong
 * @Date: 2020/9/1 14:23
 */
@Data
public class Points {
    private String coord_type_input;
    private Long loc_time;
    private Double latitude;
    private Double longitude;
    private BigDecimal acceleration;
}
