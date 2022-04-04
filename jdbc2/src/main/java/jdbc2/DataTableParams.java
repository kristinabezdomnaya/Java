package jdbc2;

import lombok.Data;

@Data
public class DataTableParams {

    private Integer token;
    private Integer orderColumnNr;

    private Integer pageStart;
    private Integer pageLength;

    private String orderDirection;
    private String searchString;




}
