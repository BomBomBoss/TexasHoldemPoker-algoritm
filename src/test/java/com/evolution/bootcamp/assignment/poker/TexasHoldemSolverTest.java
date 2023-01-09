package com.evolution.bootcamp.assignment.poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TexasHoldemSolverTest {

  @Test
  public void testTh5c6dAcAsQs() {
    assertEquals(
      "2cJc Kh4h=Ks4c Kc7h KdJs 6h7d 2hAh",
      Solver.process("texas-holdem 5c6dAcAsQs Ks4c KdJs 2hAh Kh4h Kc7h 6h7d 2cJc"));
  }

  @Test
  public void testTh2h5c8sAsKc() {
    assertEquals(
      "Jc6s Qs9h 3cKh KdQh",
      Solver.process("texas-holdem 2h5c8sAsKc Qs9h KdQh 3cKh Jc6s"));
  }

  @Test
  public void testTh3d4s5dJsQd() {
    assertEquals(
      "9h7h 2dTc KcAs 7sJd TsJc Qh8c 5c4h",
      Solver.process("texas-holdem 3d4s5dJsQd 5c4h 7sJd KcAs 9h7h 2dTc Qh8c TsJc"));
  }
  @Test
  public void test1FromReadme() {
    assertEquals(
            "Ac4d=Ad4s 5d6d As9s KhKd",
            Solver.process("texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d"));
  }

  @Test
  public void test2FromReadme() {
    assertEquals(
            "KdKs 9hJh",
            Solver.process("texas-holdem 2h3h4h5d8d KdKs 9hJh"));
  }

  @Test
  public void test3FullHouse() {
    assertEquals(
            "3d3s 4h4s",
            Solver.process("texas-holdem 2h2d4h2s8d 3d3s 4h4s"));
  }

}
