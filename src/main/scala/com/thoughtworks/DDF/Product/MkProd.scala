package com.thoughtworks.DDF.Product

import com.thoughtworks.DDF.Arrow.Arr

trait MkProd[Info[_], Repr[_]] extends ProdInfo[Info, Repr] with Arr[Info, Repr] {
  def mkProduct[A, B](implicit ai: Info[A], bi: Info[B]): Repr[A => B => (A, B)]

  final def mkProduct_[A, B](a: Repr[A])(implicit bi: Info[B]): Repr[B => (A, B)] =
    app(mkProduct(reprInfo(a), bi))(a)

  final def mkProduct__[A, B]: Repr[A] => Repr[B] => Repr[(A, B)] = a => b =>
    app(mkProduct_(a)(reprInfo(b)))(b)
}