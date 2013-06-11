//
//  OTSPredicate.h
//  OneStore
//
//  Created by Yim Daniel on 13-1-16.
//  Copyright (c) 2013年 OneStore. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGPredicate : NSObject

// 检查用户名
+(BOOL)checkUserName:(NSString *)aCandidate;

// 检查电话号码
+(BOOL)checkPhoneNumber:(NSString *)aCandidate;

// 检查数字
+(BOOL)checkNumeric:(NSString *)aCandidate;

// 检查字符
+(BOOL)checkCharacter:(NSString *)aCandidate;

// 检查密码
+(BOOL)checkPassword:(NSString *)aCandidate;

// 检查特殊字符
+(BOOL)checkSpecialChar:(NSString *)aCandidate;

// 检查邮箱
+(BOOL)checkEmail:(NSString *)aCandidate;

@end
