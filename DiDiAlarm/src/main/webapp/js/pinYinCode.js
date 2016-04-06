 var $PinYin = new Object();
        $PinYin.ds = {};
        $PinYin.$element;
        $PinYin.change = function(ch) { for (var n in $PinYin.ds) { if ($PinYin.ds[n].indexOf(ch) != -1) return n; } return ch; }
        $PinYin.search = function(val) {
            var py = "";
            for (var i = 0; i < val.length; i++) {
                var ch = val.charAt(i);
                if (/^[\u4E00-\u9FA5]$/.test(ch)) ch = $PinYin.change(ch);
                py += ch
            }
            return py;
        }
        $PinYin.searchCap = function(val) {
            var py = "";
            for (var i = 0; i < val.length; i++) {
                var ch = val.charAt(i);
                if (/^[\u4E00-\u9FA5]$/.test(ch)) ch = $PinYin.change(ch);
                py += ch.charAt(0);
            }
            //return py;
           this.element.val(py);
        } 
