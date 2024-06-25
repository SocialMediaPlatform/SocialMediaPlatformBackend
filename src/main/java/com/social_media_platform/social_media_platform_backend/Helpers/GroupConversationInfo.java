package com.social_media_platform.social_media_platform_backend.Helpers;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupConversationInfo {
    private Long conversationId;
    private List<String> usernames;
}
