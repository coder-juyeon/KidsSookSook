package com.app.mypage.dao;

import org.apache.ibatis.session.SqlSession;

import com.app.fieldTrip.domain.FieldTripDTO;
import com.app.mybatis.config.MyBatisConfig;

public class MyPageDAO {
	public SqlSession sqlSession;
	
	public MyPageDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}

//	체험학습 구매 목록 조회
	public FieldTripDTO findPurchaseList(Long userId) {
		return sqlSession.selectOne("fieldTrip.findPurchaseList", userId);
	}
	
//	내정보 불러오기
//	public MemberDTO myInfoAllSelect(Long userId) {
//		return sqlSession.selectOne("member.myInfoAllSelect", userId);
//	}
	
	/*
	 * public BoardDTO select(Long boardId) { return
	 * sqlSession.selectOne("board.select", boardId); }
	 */
}
