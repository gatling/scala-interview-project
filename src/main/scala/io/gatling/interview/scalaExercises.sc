def concat(opt1: Option[String], opt2: Option[String]): Option[String] = ???

concat(Some("foo"), Some("bar")) // Some("foobar")
concat(Some("foo"), None) // None
concat(None, Some("bar")) // None
concat(None, None) // None
