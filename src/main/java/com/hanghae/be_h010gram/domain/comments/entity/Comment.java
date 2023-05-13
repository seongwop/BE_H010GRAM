package com.hanghae.be_h010gram.domain.comments.entity;


import com.hanghae.be_h010gram.domain.comments.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.posts.Post;
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

    public Comment(CommentRequestDto commentRequestDto, Post post, Member member) {
        this.content = commentRequestDto.getContent();
        this.post = post;
        this.member = member;
        post.getComments().add(this);
    }

    public void modify(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
