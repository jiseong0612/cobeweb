package org.zerock.mapper;

import org.zerock.domain.UserDTO;

public interface MemberInsert {
	
	int insert(UserDTO user);
	
	int authInsert(UserDTO user);
}
