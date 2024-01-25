package kr.co.smart.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageVo {// 페이징 처리
//    페이지당 보여질 목록 수 : 4
//    블럭당 보여질 페이지의 수 : 3
//    총 목록수 : 30
//    총 페이지수 : 8 페이지 = 30 / 4 = 7 ... 2
//    총 블록수 : 3 블록 = 8 / 3 = 2 ... 2
//
//    각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수
//    각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)
//
//    블록번호 : 페이지번호 / 블록당 보여질 페이지수
//    각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
//    각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
//    마지막 블럭에서 끝 페이지번호가 총 페이지수보다 클 수 없으므로
//    총 페이지수를 끝 페이지번호로 한다.
        private int pageList = 10, blockPage = 10;

        private int totalList, totalPage, totalBlock, beginList, endList, curBlock, curPage = 1, beginPage, endPage;

       public void setTotalList(int totalList){
               this.totalList = totalList;

               totalPage = totalList/pageList;
               if(totalList % pageList>0) ++totalPage;

               totalBlock = totalPage/blockPage;
               if(totalPage % blockPage>0) ++totalBlock;

               endList = totalList -(curPage-1)* pageList;
               beginList = endList - (pageList-1);

               curBlock = curPage/ blockPage;
               if(curPage % blockPage >0) ++curBlock;

               endPage = curBlock*blockPage;
               beginPage = endPage -(blockPage-1);

               if(totalPage<endPage) endPage = totalPage;

       }

}
