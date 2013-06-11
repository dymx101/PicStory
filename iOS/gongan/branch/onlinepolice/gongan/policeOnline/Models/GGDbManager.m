//
//  GGDbManager.m
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGDbManager.h"
#import "FMDatabase.h"
#import "GGPoliceman.h"
#import "GGWanted.h"

static NSString *createPolicemanTableSQL = @"CREATE TABLE IF NOT EXISTS 'Policeman' ( \
'id' INTEGER PRIMARY KEY NOT NULL , \
'name' VARCHAR(30), \
'gender' VARCHAR(10), \
'number' VARCHAR(30), \
'phone' VARCHAR(30), \
'photo' VARCHAR(100), \
'stationName' VARCHAR(30), \
'stationPhone' VARCHAR(30), \
'superviserPhone' VARCHAR(30) \
)";

static NSString *createWantedTableSQL = @"CREATE TABLE IF NOT EXISTS 'Wanted' ( \
'id' INTEGER PRIMARY KEY NOT NULL , \
'title' VARCHAR(100), \
'content' TEXT, \
'type' VARCHAR(30), \
'rewardTime' VARCHAR(30), \
'wantedManName' VARCHAR(100), \
'wantedManGender' VARCHAR(30), \
'wantedManAddress' VARCHAR(100), \
'addTime' VARCHAR(30), \
'updateTime' VARCHAR(30) \
)";

static NSString *insertPolicemanSQL = @"insert into 'Policeman' (id, name, gender, number, phone, photo, stationName, stationPhone, superviserPhone) values(%lld, %@, %@, %@, %@, %@, %@, %@, %@)";
static NSString *deletePolicemanSQL = @"delete from Policeman where id=%lld ";
static NSString *selectPolicemanSQL = @"select * from 'Policeman'";
static NSString *selectPolicemanWithIdSQL = @"select * from 'Policeman' where id=%lld ";

static NSString *insertWantedSQL = @"insert into 'Wanted' (id, title, content, type, rewardTime, wantedManName, wantedManGender, wantedManAddress, addTime, updateTime) values(%lld, %@, %@, %@, %@, %@, %@, %@, %@, %@)";
static NSString *deleteWantedSQL = @"delete from Wanted where id=%lld ";
static NSString *selectWantedSQL = @"select * from 'Wanted'";
static NSString *selectWantedWithIdSQL = @"select * from 'Wanted' where id=%lld ";

@implementation GGDbManager
DEF_SINGLETON(GGDbManager)

-(NSString *)_dbPath
{
    static NSString *dbPath;
    if (!dbPath)
    {
        dbPath = [PATH_OF_DOCUMENT stringByAppendingPathComponent:@"gongan.sqlite"];
    }
    
    return dbPath;
}

- (id)init
{
    self = [super init];
    if (self) {
        [self _initDB];
    }
    return self;
}

-(FMDatabase *)_db
{
    return [FMDatabase databaseWithPath:[self _dbPath]];;
}

-(void)_initDB
{
    if (![[NSFileManager defaultManager] fileExistsAtPath:[self _dbPath]])
    {
        FMDatabase *db = [self _db];
        if ([db open])
        {
            [db executeUpdate:createPolicemanTableSQL];
            [db executeUpdate:createWantedTableSQL];
            [db close];
        }
    }
}

-(BOOL)insertPoliceman:(GGPoliceman *)aPoliceman
{
    BOOL success = NO;
    FMDatabase *db = [self _db];
    if (aPoliceman && [db open])
    {
//        [db executeUpdate:insertPolicemanSQL, aPoliceman.ID, aPoliceman.name, aPoliceman.gender, aPoliceman.number, aPoliceman.phone, aPoliceman.photo, aPoliceman.stationName, aPoliceman.stationPhone, aPoliceman.superviserPhone];
        success = [db executeUpdateWithFormat:insertPolicemanSQL, aPoliceman.ID, aPoliceman.name, aPoliceman.gender, aPoliceman.number, aPoliceman.phone, aPoliceman.photo, aPoliceman.stationName, aPoliceman.stationPhone, aPoliceman.superviserPhone];
        [db close];
    }
    
    return success;
}

-(BOOL)deletePolicemanByID:(long long)aPolicemanID
{
    BOOL success = NO;
    FMDatabase *db = [self _db];
    if ([db open])
    {
        NSString *sql = [NSString stringWithFormat:deletePolicemanSQL, aPolicemanID];
        success = [db executeUpdate:sql];
        [db close];
    }
    
    return success;
}

-(BOOL)hasPolicemanWithID:(long long)aPolicemanID
{
    FMDatabase *db = [self _db];
    if ([db open])
    {
        NSString *sql = [NSString stringWithFormat:selectPolicemanWithIdSQL, aPolicemanID];
        FMResultSet *rs = [db executeQuery:sql];
        while ([rs next])
        {
            [db close];
            return YES;
        }
        [db close];
    }
    
    return NO;
}

-(NSArray *)getAllPolicemans
{
    NSMutableArray *policemans;
    
    FMDatabase *db = [self _db];
    if ([db open])
    {
        policemans = [NSMutableArray array];
        FMResultSet *rs = [db executeQuery:selectPolicemanSQL];
        while ([rs next])
        {
            GGPoliceman *policeman = [GGPoliceman model];
            
            policeman.ID = [rs longLongIntForColumn:@"id"];
            policeman.name = [rs stringForColumn:@"name"];
            policeman.gender = [rs stringForColumn:@"gender"];
            policeman.number = [rs stringForColumn:@"number"];
            policeman.phone = [rs stringForColumn:@"phone"];
            policeman.photo = [rs stringForColumn:@"photo"];
            policeman.stationName = [rs stringForColumn:@"stationName"];
            policeman.stationPhone = [rs stringForColumn:@"stationPhone"];
            policeman.superviserPhone = [rs stringForColumn:@"superviserPhone"];
            
            [policemans addObject:policeman];
        }
        [db close];
    }
    
    return policemans;
}

-(BOOL)insertWanted:(GGWanted *)aWanted
{
    BOOL success = NO;
    FMDatabase *db = [self _db];
    if (aWanted && [db open])
    {
//        [db executeUpdate:insertWantedSQL,aWanted.ID, aWanted.title, aWanted.content, aWanted.type, aWanted.rewardTime, aWanted.wantedManName, aWanted.wantedManGender, aWanted.wantedManAddress, aWanted.addTime, aWanted.updateTime];
        success = [db executeUpdateWithFormat:insertWantedSQL,aWanted.ID, aWanted.title, aWanted.content, aWanted.type, aWanted.rewardTime, aWanted.wantedManName, aWanted.wantedManGender, aWanted.wantedManAddress, aWanted.addTime, aWanted.updateTime];
        [db close];
    }
    
    return success;
}

-(BOOL)deleteWantedByID:(long long)aWantedID
{
    BOOL success = NO;
    FMDatabase *db = [self _db];
    if ([db open])
    {
        NSString *sql = [NSString stringWithFormat:deleteWantedSQL, aWantedID];
        success = [db executeUpdate:sql];
        [db close];
    }
    
    return success;
}

-(NSArray *)getAllWanted
{
    NSMutableArray *wanted;
    
    FMDatabase *db = [self _db];
    if ([db open])
    {
        wanted = [NSMutableArray array];
        FMResultSet *rs = [db executeQuery:selectWantedSQL];
        while ([rs next])
        {
            GGWanted *policeman = [GGWanted model];
            
            policeman.ID = [rs longLongIntForColumn:@"id"];
            policeman.title = [rs stringForColumn:@"title"];
            policeman.content = [rs stringForColumn:@"content"];
            policeman.type = [rs stringForColumn:@"type"];
            policeman.rewardTime = [rs stringForColumn:@"rewardTime"];
            policeman.wantedManName = [rs stringForColumn:@"wantedManName"];
            policeman.wantedManGender = [rs stringForColumn:@"wantedManGender"];
            policeman.wantedManAddress = [rs stringForColumn:@"wantedManAddress"];
            policeman.addTime = [rs stringForColumn:@"addTime"];
            policeman.updateTime = [rs stringForColumn:@"updateTime"];
            
            [wanted addObject:policeman];
        }
        [db close];
    }
    
    return wanted;
}

-(BOOL)hasWantedWithID:(long long)aWantedID
{
    FMDatabase *db = [self _db];
    if ([db open])
    {
        NSString *sql = [NSString stringWithFormat:selectWantedWithIdSQL, aWantedID];
        FMResultSet *rs = [db executeQuery:sql];
        while ([rs next])
        {
            [db close];
            return YES;
        }
        [db close];
    }
    
    return NO;
}

@end
