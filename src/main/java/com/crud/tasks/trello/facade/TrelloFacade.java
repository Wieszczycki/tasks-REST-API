package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    @Autowired
    private TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);
//        LOGGER.info("Starting filtering boards...");
//        List<TrelloBoard> filteredBoards = trelloBoards.stream()
//                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
//                .collect(Collectors.toList());
//        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return trelloMapper.mapToBoardsDto(filteredBoards);
        //return trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        //List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();
        //return trelloBoardDto.stream()
        //        .map(trelloBoardDto -> trelloMapper.mapToBoard(trelloBoardDto))
        //        .collect(toList());
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
