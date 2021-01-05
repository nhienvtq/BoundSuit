package iKi.com

class datasource {
    companion object{
        fun createDataSet(): ArrayList<datamodel>{
            val list = ArrayList<datamodel>()
            list.add(datamodel("dt1", "dt2", "dt3"))
            list.add(datamodel("dt1a", "dt2a", "dt3a"))
            list.add(datamodel("dt1b", "dt2b", "dt3b"))
            list.add(datamodel("dt1c", "dt2c", "dt3c"))
            return list
        }
    }
}