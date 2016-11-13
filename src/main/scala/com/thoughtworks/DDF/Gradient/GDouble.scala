package com.thoughtworks.DDF.Gradient
import com.thoughtworks.DDF.Language.{LangInfoG, LangTerm, NextLang}

trait GDouble extends Gradient[Double] {
  override implicit def GInfo: LangInfoG[Double] = ltl.doubleInfo

  override def constG: LangTerm[Double] = ltl.litD(0)

  def lift: LangTerm[Double => Double] =>
    LangTerm[((Double, Double)) => Double] =>
      LangTerm[((Double, Double)) => (Double, Double)] = origf => gradf => {
    ltl.S__(ltl.B__(ltl.mkProd)(ltl.B__(origf)(ltl.zro[Double, Double])))(gradf)
  }

  def lift2: LangTerm[Double => Double => Double] =>
    LangTerm[((Double, Double)) => ((Double, Double)) => Double] =>
      LangTerm[((Double, Double)) => ((Double, Double)) => (Double, Double)] = origf => gradf => {
    ltl.curry_(ltl.S__(
      ltl.B__(ltl.mkProd)(
        ltl.uncurry_(ltl.C_(ltl.B__(ltl.C_(ltl.B__(origf)(ltl.zro[Double, Double])))(ltl.zro[Double, Double])))))(
      ltl.uncurry_(gradf)))
  }

  override def plus: LangTerm[((Double, Double)) => ((Double, Double)) => (Double, Double)] =
    lift2(ltl.plusD)(ltl.B__(ltl.C_(ltl.B__(ltl.plusD)(ltl.fst[Double, Double])))(ltl.fst[Double, Double]))

  override def mult: LangTerm[((Double, Double)) => ((Double, Double)) => (Double, Double)] =
    lift2(ltl.multD)({
      val l = NextLang.apply[LangInfoG, LangTerm, (Double, Double)](ltl, ltl.prodInfo(ltl.doubleInfo, ltl.doubleInfo))
      val r = NextLang.apply[LangInfoG, l.repr, (Double, Double)](l, ltl.prodInfo(ltl.doubleInfo, ltl.doubleInfo))
      l.collapse(r.collapse(
        r.plusD__(r.multD__(r.zro_(r.rconv(l.in)))(r.fst_(r.in)))(r.multD__(r.zro_(r.in))(r.fst_(r.rconv(l.in))))))
    })

  override def div: LangTerm[((Double, Double)) => ((Double, Double)) => (Double, Double)] = lift2(ltl.divD)(???)

  override def sig: LangTerm[((Double, Double)) => (Double, Double)] = lift(ltl.sigD)(???)

  override def exp: LangTerm[((Double, Double)) => (Double, Double)] = lift(ltl.expD)(???)
}

object GDouble extends GDouble