package miruy.dev.frety.controller;

import lombok.RequiredArgsConstructor;
import miruy.dev.frety.entity.Chord;
import miruy.dev.frety.service.ChordService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final ChordService chordService;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {

            model.addAttribute("results", Page.empty());  // 빈 결과
            model.addAttribute("keyword", keyword);

            // SEO metadata
            model.addAttribute("title", "악보 검색");
            model.addAttribute("description", "검색어를 입력해주세요.");
            return "searchResults";
        }

        // 페이지네이션과 검색어를 통해 코드 검색
        Page<Chord> results = chordService.searchChords(keyword, page, 10);

        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);

        // SEO metadata
        model.addAttribute("title", "악보 검색 | " + keyword);
        model.addAttribute("description", keyword + " 관련 악보 검색 결과입니다.");

        return "searchResults";
    }

}
