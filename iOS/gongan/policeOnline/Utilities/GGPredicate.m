//
//  OTSPredicate.m
//  OneStore
//
//  Created by Yim Daniel on 13-1-16.
//  Copyright (c) 2013年 OneStore. All rights reserved.
//

#import "GGPredicate.h"


#define PRED_CONDITION_MOBILE       @"[0-9]{8,14}"
#define PRED_CONDITION_NUMERIC      @"[0-9]+"
#define PRED_CONDITION_CHARACTER    @"[A-Z0-9a-z]+"
#define PRED_CONDITION_PASSWORD     @"[^\\n\\s]{6,12}"
#define PRED_CONDITION_SPECIAL_CHAR @"[A-Z0-9a-z._%+-@]+"
#define PRED_CONDITION_EMAIL        @"^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"


@implementation GGPredicate

+(BOOL)checkUserName:(NSString *)aCandidate
{
    return [self checkPhoneNumber:aCandidate] || [self checkEmail:aCandidate];
}

+(BOOL)checkPhoneNumber:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_MOBILE];
}

+(BOOL)checkNumeric:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_NUMERIC];
}

+(BOOL)checkCharacter:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_CHARACTER];
}

+(BOOL)checkPassword:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_PASSWORD];
}

+(BOOL)checkSpecialChar:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_SPECIAL_CHAR];
}

+(BOOL)checkEmail:(NSString *)aCandidate
{
    return [self __checkCandidate:aCandidate condition:PRED_CONDITION_EMAIL];
}


+ (BOOL)__checkCandidate: (NSString *) aCandidate condition: (NSString*) aCondition
{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", aCondition];
    return [predicate evaluateWithObject:aCandidate];
}
@end
