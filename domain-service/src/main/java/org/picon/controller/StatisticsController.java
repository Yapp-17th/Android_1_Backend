package org.picon.controller;

import lombok.RequiredArgsConstructor;
import org.picon.domain.Member;
import org.picon.domain.Post;
import org.picon.dto.*;
import org.picon.repository.MemberRepository;
import org.picon.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/domain/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/{year}/{month}")
    public StatisticsDto getPostsByStatistics(@PathVariable("year") int year, @PathVariable("month") int month, @RequestParam("identity") String identity) {
        Member member = memberRepository.findByIdentity(identity).orElseThrow(EntityNotFoundException::new);
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month + 1, 1);
        List<Post> posts = postRepository.findAllByMemberAndCreateMonth(member, startDate, endDate).orElseThrow(EntityNotFoundException::new);
        List<EmotionCount> emotionCounts = posts.stream()
                .collect(groupingBy(Post::getEmotion))
                .entrySet().stream()
                .map(entry -> new EmotionCount(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparing(EmotionCount::getCount).reversed())
                .collect(Collectors.toList());

        List<AddressCount> addressCounts = posts.stream()
                .collect(groupingBy(Post::getAddress))
                .entrySet().stream()
                .map(addressListEntry ->
                        new AddressCount(addressListEntry.getKey().getAddrCity(),
                                addressListEntry.getKey().getAddrGu(),
                                addressListEntry.getValue()
                                        .stream()
                                        .collect(groupingBy(Post::getEmotion))
                                        .entrySet()
                                        .stream()
                                        .map(emotionListEntry -> new EmotionCount(emotionListEntry.getKey(), emotionListEntry.getValue().size()))
                                        .collect(toList()),
                                addressListEntry.getValue().size()))
                .collect(Collectors.toList());

        List<AddressCount> sortedAddressCounts = addressCounts.stream()
                .sorted(Comparator.comparing(AddressCount::getTotal).reversed())
                .limit(5)
                .collect(toList());

        return new StatisticsDto(emotionCounts, posts.size(), sortedAddressCounts);
    }
}
