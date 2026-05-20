package com.example.board.controller;

import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** GET /api/posts           → 전체 목록 */
    /** GET /api/posts?keyword=X → 검색 결과 */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(
            @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(postService.searchPosts(keyword));
        }
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /** GET /api/posts/{id} → 상세 조회 (조회수 증가) */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    /** GET /api/posts/{id}/edit → 수정용 조회 (조회수 증가 없음) */
    @GetMapping("/{id}/edit")
    public ResponseEntity<PostResponseDto> getPostForEdit(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostForEdit(id));
    }

    /** POST /api/posts → 게시글 등록 */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(dto));
    }

    /** PUT /api/posts/{id} → 게시글 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    /** DELETE /api/posts/{id} → 게시글 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
