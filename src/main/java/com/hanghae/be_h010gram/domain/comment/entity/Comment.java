package com.hanghae.be_h010gram.domain.comment.entity;


import com.hanghae.be_h010gram.domain.comment.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.util.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    public Post post;

    @ColumnDefault("0")
    private int liked;

    public Comment(CommentRequestDto commentRequestDto, Post post, Member member) {
        this.content = commentRequestDto.getContent();
        this.post = post;
        this.member = member;
    }

    public void updateLike(boolean likeOrDislike) {
        this.liked = likeOrDislike ? this.liked + 1 : this.liked - 1;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void modify(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
