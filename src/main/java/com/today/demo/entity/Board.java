package com.today.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "member_b_id"))
    private Member member;

    private String content;

    @ColumnDefault("0")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", foreignKey = @ForeignKey(name = "cate_b_id"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marker_id", foreignKey = @ForeignKey(name = "marker_b_id"))
    private Marker marker;

    private String address;

    @OneToMany(mappedBy = "board")
    private List<Images> boardImgDtoList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Heart> hearts = new ArrayList<>();

    public void addCount(){
        this.likeCount += 1;
    }
    public void cenCount(){
        this.likeCount -= 1;
    }
}
