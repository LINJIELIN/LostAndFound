package com.linjie.lostfound.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: Sort
 * Description: TODO
 * author: LINJIE
 * date: 2019/4/21 10:39
 * version V1.0
 */
@Entity
public class Sort {

    @Id
    @TableGenerator(name="SORT_GENERATOR",
            table ="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="sort",
            valueColumnName="PK_VALUE",
            initialValue=7000000,
            allocationSize=1
    )
    @GeneratedValue(strategy= GenerationType.TABLE, generator="SORT_GENERATOR")
    @Column(name = "s_id")
    private Long id;

    @Column(name = "s_name")
    private String name;

//    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,optional = true)
//    private Lost lost;
//
//    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,optional = true)
//    private Found found;

    @OneToMany(mappedBy = "sort")
    @JsonIgnore
    private List<Lost> lost;
    @OneToMany(mappedBy = "sort")
    @JsonIgnore
    private List<Found> found;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lost> getLost() {
        return lost;
    }

    public void setLost(List<Lost> lost) {
        this.lost = lost;
    }

    public List<Found> getFound() {
        return found;
    }

    public void setFound(List<Found> found) {
        this.found = found;
    }
}
