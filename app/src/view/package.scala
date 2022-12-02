package object view {
    extension (component: scala.swing.Component) {
        def forceSize(width: Int, height: Int): Unit = {
            val dimension = {
                new scala.swing.Dimension(width, height)
            }
            component.preferredSize = dimension
            component.minimumSize = dimension
            component.maximumSize = dimension
        }
    }
}
