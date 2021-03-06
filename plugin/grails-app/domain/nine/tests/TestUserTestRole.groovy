/*
* Copyright 2019 Yak.Works - Licensed under the Apache License, Version 2.0 (the "License")
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*/
package nine.tests

import groovy.transform.CompileDynamic

import org.apache.commons.lang.builder.HashCodeBuilder

@CompileDynamic
class TestUserTestRole implements Serializable {

    TestUser testUser
    TestRole testRole

    boolean equals(Object other) {
        if (!(other instanceof TestUserTestRole)) {
            return false
        }

        other.testUser?.id == testUser?.id &&
                other.testRole?.id == testRole?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (testUser) builder.append(testUser.id)
        if (testRole) builder.append(testRole.id)
        builder.toHashCode()
    }

    static TestUserTestRole get(long testUserId, long testRoleId) {
        find 'from TestUserTestRole where testUser.id=:testUserId and testRole.id=:testRoleId',
                [testUserId: testUserId, testRoleId: testRoleId]
    }

    static TestUserTestRole create(TestUser testUser, TestRole testRole, boolean flush = false) {
        new TestUserTestRole(testUser: testUser, testRole: testRole).save(flush: flush, insert: true)
    }

    static boolean remove(TestUser testUser, TestRole testRole, boolean flush = false) {
        TestUserTestRole instance = TestUserTestRole.findByTestUserAndTestRole(testUser, testRole)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(TestUser testUser) {
        executeUpdate 'DELETE FROM TestUserTestRole WHERE testUser=:testUser', [testUser: testUser]
    }

    static void removeAll(TestRole testRole) {
        executeUpdate 'DELETE FROM TestUserTestRole WHERE testRole=:testRole', [testRole: testRole]
    }

    static mapping = {
        id composite: ['testRole', 'testUser']
        version false
    }
}
