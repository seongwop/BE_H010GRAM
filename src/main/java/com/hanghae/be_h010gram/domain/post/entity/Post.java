package com.hanghae.be_h010gram.domain.post.entity;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.dto.PostRequestDto;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

import static com.hanghae.be_h010gram.exception.ExceptionEnum.POST_CONTENT_NOT_FOUND;
import static com.hanghae.be_h010gram.exception.ExceptionEnum.USER_NOT_FOUND;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ColumnDefault("0")
    private int liked;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String postImage;

    public Post(PostRequestDto postRequestDto, Member member) {
        if (member.getId() == null) {
            throw new CustomException(USER_NOT_FOUND);
        }

        if (postRequestDto.getContent() == null || postRequestDto.getContent().isEmpty()) {
            throw new CustomException(POST_CONTENT_NOT_FOUND);
        }

        this.content = postRequestDto.getContent();
        this.member = member;
        this.postImage = getPostImage();
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }

    public void updateLike(boolean likeOrDislike) {
        this.liked = likeOrDislike ? this.liked + 1 : this.liked - 1;
    }
}
