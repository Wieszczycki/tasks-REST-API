package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrelloMapperTest {


    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapToBoardDto() {
        //given
        TrelloList trelloList1 = new TrelloList("101", "TestList101", false);
        TrelloList trelloList2 = new TrelloList("102", "TestList102", true);
        List<TrelloList> trelloLists = Arrays.asList(trelloList1, trelloList2);
        TrelloBoard trelloBoard = new TrelloBoard("1", "TestBoard1", trelloLists);
        List<TrelloBoard> trelloBoardList = Arrays.asList(trelloBoard);

        //when
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //then
        assertNotEquals(trelloBoardList, trelloBoardDtoList);
        assertEquals(1, trelloBoardDtoList.size());
        assertEquals(2, trelloBoardDtoList.get(0).getLists().size());
        assertEquals("101", trelloBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals("TestList101", trelloBoardDtoList.get(0).getLists().get(0).getName());
        assertTrue(trelloBoardDtoList.get(0).getLists().get(1).isClosed());
    }

    @Test
    public void testMapToBoard() {
        TrelloListDto trelloListDto1 = new TrelloListDto("103", "TestList103", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("104", "TestList102", false);
        List<TrelloListDto> trelloLists = Arrays.asList(trelloListDto1, trelloListDto2);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "TestBoard1", trelloLists);
        List<TrelloBoardDto> trelloBoardListDto = Arrays.asList(trelloBoardDto);

        //when
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardListDto);

        //then
        assertNotEquals(trelloBoardListDto, trelloBoardList);
        assertEquals(1, trelloBoardList.size());
        assertEquals(2, trelloBoardList.get(0).getLists().size());
        assertEquals("103", trelloBoardList.get(0).getLists().get(0).getId());
        assertEquals("TestList103", trelloBoardList.get(0).getLists().get(0).getName());
        assertFalse(trelloBoardList.get(0).getLists().get(1).isClosed());
    }

    @Test
    public void testMapToList() {
        //given
        TrelloListDto trelloListDto1 = new TrelloListDto("103", "TestList103", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("104", "TestList104", false);
        List<TrelloListDto> trelloListsDto = Arrays.asList(trelloListDto1, trelloListDto2);

        //when
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListsDto);

        //then
        assertNotEquals(trelloListsDto, trelloLists);
        assertEquals(2, trelloLists.size());
        assertEquals("103", trelloLists.get(0).getId());
        assertEquals("TestList104", trelloLists.get(1).getName());
        assertFalse(trelloLists.get(1).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //given
        TrelloList trelloList1 = new TrelloList("103", "TestList103", true);
        TrelloList trelloList2 = new TrelloList("104", "TestList104", false);
        List<TrelloList> trelloLists = Arrays.asList(trelloList1, trelloList2);

        //when
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListDto(trelloLists);

        //then
        assertNotEquals(trelloLists, trelloListsDto);
        assertEquals(2, trelloListsDto.size());
        assertEquals("103", trelloListsDto.get(0).getId());
        assertEquals("TestList104", trelloListsDto.get(1).getName());
        assertFalse(trelloListsDto.get(1).isClosed());
    }

    @Test
    public void testMapToCard() {
        //given
        TrelloCardDto trelloCardDto1 = new TrelloCardDto("Card1", "desc1", "1", "101");

        //when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto1);

        //then
        assertEquals("Card1", trelloCard.getName());
        assertEquals("desc1", trelloCard.getDescription());
        assertEquals("1", trelloCard.getPos());
        assertEquals("101", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //given
        TrelloCard trelloCard1 = new TrelloCard("Card1", "desc1", "1", "101");

        //when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard1);

        //then
        assertEquals("Card1", trelloCardDto.getName());
        assertEquals("desc1", trelloCardDto.getDescription());
        assertEquals("1", trelloCardDto.getPos());
        assertEquals("101", trelloCardDto.getListId());
    }
}