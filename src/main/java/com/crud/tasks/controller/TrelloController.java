package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    @Autowired
    private final TrelloFacade trelloFacade;
//    private final TrelloService trelloService;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloFacade.fetchTrelloBoards();

//        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();
//
//        trelloBoards.forEach(trelloBoardDto -> {
//            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
//            System.out.println("This board contains lists: ");
//            trelloBoardDto.getLists().forEach(trelloList -> {
//                System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
//            });
//        });
    }

    @PostMapping("createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
//        return trelloService.createTrelloCard(trelloCardDto);
        return trelloFacade.createCard(trelloCardDto);
    }
}