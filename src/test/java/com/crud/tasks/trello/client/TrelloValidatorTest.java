package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTest {

    @Test
    public void testValidateTrelloBoards() {
        //given
        TrelloValidator trelloValidator = new TrelloValidator();

        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "list1", false));

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test", trelloLists));
        trelloBoards.add(new TrelloBoard("2", "board2", trelloLists));

        //when
        List<TrelloBoard> expectedTrelloBoardList = trelloValidator.validateTrelloBoards(trelloBoards);

        //then
        assertNotNull(expectedTrelloBoardList);
        assertEquals(1, expectedTrelloBoardList.size());
        expectedTrelloBoardList.forEach(trelloBoard -> {
            assertEquals("2", trelloBoard.getId());
            assertEquals("board2", trelloBoard.getName());
        });
    }
}